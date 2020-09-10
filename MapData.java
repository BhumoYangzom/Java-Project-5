import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * MapData class reads data from the file in the directory "Data" and computes
 * the maximum, minimum and average for the variables srad (Solar radiation),
 * tair (air temperature), ta9m (air temperature at 9 meters). It produces a
 * catalog with time.
 * 
 * @author apotsangyangzom
 * @version 2018 - 10- 14
 */

public class MapData
{
    /**
     * A HashMap that stores a list of data in an arraylist called dataCatalog with
     * a String as a key.
     */
    private HashMap<String, ArrayList<Observation>> dataCatalog;
    /**
     * An EnumMap called statistics
     */
    private EnumMap<StatsType, TreeMap<String, Statistics>> statistics;
    /**
     * A TreeMap called paramPositions
     */
    private TreeMap<String, Integer> paramPositions;
    // private int NUMBER_OF_MISSING_OBSERVATIONS = 10;

    /**
     * Number of stations
     */
    private Integer numberOfStations = null;
    /**
     * Air temperature at 9 meter
     */
    private String TA9M = "TA9M";
    /**
     * A variable to represent air temperature
     */
    private String TAIR = "TAIR";
    /**
     * A variable to represent solar radiation
     */
    private String SRAD = "SRAD";
    /**
     * A variable for Station ID.
     */
    private String STID = "STID";
    /**
     * A constant string MESONET with a value Mesonet.
     */
    private final String MESONET = "Mesonet";
    /**
     * A string called fileName
     */
    private String fileName;
    /**
     * A GregorianCalendar called utcDateTime
     */
    private GregorianCalendar utcDateTime;

    /**
     * MapData Constructor
     * 
     * @param year: Year of file creation
     * @param month: Month of data file creation
     * @param day: Day of data file creation
     * @param hour: Hour of data file creation
     * @param minute: Minute of data file creation
     * @param directory: The directory in which the data file is located.
     */

    public MapData(int year, int month, int day, int hour, int minute, String directory)
    {
        this.utcDateTime = new GregorianCalendar(year, month, day, hour, minute);
        directory = "data";
        this.fileName = createFileName(year, month, day, hour, minute, directory);
    }

    /**
     * A method to create FileName.
     * 
     * @param year: Year of file creation
     * @param month: Month of data file creation
     * @param day: Day of data file creation
     * @param hour: Hour of data file creation
     * @param minute: Minute of data file creation
     * @param directory: Directory in which the data file is located
     * @return the File Name.
     */

    public String createFileName(int year, int month, int day, int hour, int minute, String directory)
    {
        String Year = Integer.toString(year);
        String Month = String.format("0%d", month);
        String Day = Integer.toString(day);
        String Hr = Integer.toString(hour);
        String Mint = Integer.toString(minute);
        String FileName = String.format("%s%s%s%s%s.mdf", Year, Month, Day, Hr, Mint);
        return FileName;
    }

    /**
     * A method to parse headers and put them in the TreeMap paramPositions.
     * 
     * @param inParamStr: headers from the data file.
     */

    private void parseParamHeader(String inParamStr)
    {
        paramPositions = new TreeMap<String, Integer>();
        String[] headerArray = inParamStr.trim().split("\\s+");
        for (Integer i = 0; i < headerArray.length; ++i)
        {
            // System.out.println(headerArray[i]);
            paramPositions.put(headerArray[i], i);
        }
    }

    /**
     * A method to get the index number of the headers.
     * 
     * @param inParamStr
     * @return An Integer value of the index values.
     */
    public Integer getIndexOf(String inParamStr)
    {
        return this.paramPositions.get(inParamStr);
    }

    // ArrayList<Observation> dataFromFile = new ArrayList<Observation>();
    /**
     * A method that parses the file
     * 
     * @throws IOException
     */
    public void parseFile() throws IOException
    {
        // String fileName = createFileName(2018, 8, 30, 17, 45, "data");

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        br.readLine();
        br.readLine();
        String headers = br.readLine();
        String[] headerArray = headers.trim().split("\\s+");
        parseParamHeader(headers);
        prepareDataCatalog();
        numberOfStations = 0;

        String line = br.readLine();
// loop to read file vertically
        while (line != null)
        {
            String[] lineArrays = line.trim().split("\\s+");
            // a for loop to read file horizontally
            for (int i = 1; i < headerArray.length; i++)
            {
                if (!headerArray[i].equals(STID))
                {
                    Observation data = new Observation(Double.parseDouble(lineArrays[getIndexOf(headerArray[i])]),
                            lineArrays[getIndexOf(STID)]);

                    dataCatalog.get(headerArray[i]).add(data);
                    dataCatalog.put(headerArray[i], dataCatalog.get(headerArray[i]));

                }
            }
            line = br.readLine();
            // increase the number of stations.
            numberOfStations++;
        }

        br.close();
    }

    /**
     * Calculates maximum, minimum, average and total values for all the data in the
     * file with their respective station IDs.
     */
    private void calculateAllStatistics()
    {
        // Created an object of EnumMap
        statistics = new EnumMap<StatsType, TreeMap<String, Statistics>>(StatsType.class);
        // Created four objects of TreeMap
        TreeMap<String, Statistics> treemap = new TreeMap<String, Statistics>();
        TreeMap<String, Statistics> treemap1 = new TreeMap<String, Statistics>();
        TreeMap<String, Statistics> treemap2 = new TreeMap<String, Statistics>();
        TreeMap<String, Statistics> treemap3 = new TreeMap<String, Statistics>();
// A Set that takes in a keyset of headers
        Set<String> parameterIds = dataCatalog.keySet();
        // loop through the headers.
        for (String paramId : parameterIds)
        {
            // to count the number of stations
            numberOfStations = 0;
            // Holds minimum
            Statistics Minimum;
            // holds maximum
            Statistics Maximum;
            // holds average
            Statistics Average;
            // holds total value
            Statistics Total;
            // to hold the station ids.
            String stid = null;

            double min = Double.MAX_VALUE;
            double max = Double.NEGATIVE_INFINITY;
            String StidForMin = "";
            String StidForMax = "";

            double total = 0.0;

            ArrayList<Observation> data = dataCatalog.get(paramId);
            for (Observation obs : data)
            {

                if (obs.isValid() && obs.getValue() > max)
                {
                    max = obs.getValue();
                    StidForMax = obs.getStid();

                }

                if (obs.isValid() && obs.getValue() < min)
                {

                    min = obs.getValue();
                    StidForMin = obs.getStid();
                }

                if (obs.isValid())
                {
                    total += obs.getValue();
                    stid = obs.getStid();
                }

                numberOfStations++;
            }
            double average = total / numberOfStations;

            Maximum = new Statistics(max, StidForMax, utcDateTime, numberOfStations, StatsType.MAXIMUM);
            Minimum = new Statistics(min, StidForMin, utcDateTime, numberOfStations, StatsType.MINIMUM);
            Average = new Statistics(average, MESONET, utcDateTime, numberOfStations, StatsType.AVERAGE);
            Total = new Statistics(total, stid, utcDateTime, numberOfStations, StatsType.TOTAL);

            treemap.put(paramId, Maximum);
            treemap1.put(paramId, Minimum);

            treemap2.put(paramId, Average);
            treemap3.put(paramId, Total);
        }
// put the values and station ids into EnumMap named statistics
        statistics.put(StatsType.MINIMUM, treemap1);
        statistics.put(StatsType.MAXIMUM, treemap);
        statistics.put(StatsType.AVERAGE, treemap2);
        statistics.put(StatsType.TOTAL, treemap3);

    }

    /**
     * Prepares a data catalog that contains the headers and their data in an
     * ArrayList.
     */
    private void prepareDataCatalog()
    {
// Created an object of HashMap named dataCatalog
        dataCatalog = new HashMap<String, ArrayList<Observation>>();
        // loop through the headers
        for (Map.Entry<String, Integer> entry : paramPositions.entrySet())
        {
            String key = entry.getKey();
            ArrayList<Observation> newArray = new ArrayList<Observation>();
            dataCatalog.put(key, newArray);
        }

    }

    /**
     * Prints out maximum, minimum, and average for TAIR, TA9M and SRAD
     * respectively.
     */

    private void calculateStatistics()
    {// calling calculateAllStatistics()
        calculateAllStatistics();
        // prints minimum, maximum and average for tair with the station IDs.
        String tair = String.format("========================================================\n"
                + "Maximum Air Temperature[1.5m] = %.1f C at %s\n" + "Minimum Air Temperature[1.5m] = %.1f C at %s\n"
                + "Average Air Temperature[1.5m] = %.1f C at %s\n"
                + "========================================================\n",

                getStatistics(StatsType.MAXIMUM, TAIR).getValue(), getStatistics(StatsType.MAXIMUM, TAIR).getStid(),

                getStatistics(StatsType.MINIMUM, TAIR).getValue(), getStatistics(StatsType.MINIMUM, TAIR).getStid(),

                getStatistics(StatsType.AVERAGE, TAIR).getValue(), MESONET);
        System.out.print(tair);
        // prints maximum, minimum, and average for ta9m with the respective station
        // IDs.
        String ta9m = String.format("========================================================\n"
                + "Maximum Air Temperature[9.0m] = %.1f C at %s\n" + "Minimum Air Temperature[9.0m] = %.1f C at %s\n"
                + "Average Air Temperature[9.0m] = %.1f C at %s\n"
                + "========================================================\n",

                getStatistics(StatsType.MAXIMUM, TA9M).getValue(), getStatistics(StatsType.MAXIMUM, TA9M).getStid(),

                getStatistics(StatsType.MINIMUM, TA9M).getValue(), getStatistics(StatsType.MINIMUM, TA9M).getStid(),

                getStatistics(StatsType.AVERAGE, TA9M).getValue(), MESONET);
        System.out.print(ta9m);
//prints maximum, minimum, and average for srad with the respective station IDs. 
        String srad = String.format(
                "========================================================\n"
                        + "Maximum Solar Radiation[1.5m] = %.1f W/m^2 at %s\n"
                        + "Minimum Solar Radiation[1.5m] = %.1f W/m^2 at %s\n"
                        + "Average Solar Radiation[1.5m] = %.1f W/m^2 at %s\n"
                        + "========================================================\n",
                getStatistics(StatsType.MAXIMUM, SRAD).getValue(), getStatistics(StatsType.MAXIMUM, SRAD).getStid(),

                getStatistics(StatsType.MINIMUM, SRAD).getValue(), getStatistics(StatsType.MINIMUM, SRAD).getStid(),

                getStatistics(StatsType.AVERAGE, SRAD).getValue(), MESONET);
        System.out.print(srad);

    }

    /**
     * Gives the maximum, minimum, and average of respective paramId.
     * 
     * @param type: MAXIMUM, MINIMUM, AVERAGE, TOTAL
     * @param paramId: Headers from the file
     * @return A statistics.
     */

    public Statistics getStatistics(StatsType type, String paramId)
    {
        return statistics.get(type).get(paramId);
    }

    public String toString()
    {
        String output = "========================================================" + "\n";
        output += "=== " + utcDateTime.get(Calendar.YEAR) + "-" + utcDateTime.get(Calendar.MONTH) + "-"
                + utcDateTime.get(Calendar.DATE) + " " + utcDateTime.get(Calendar.HOUR_OF_DAY) + ":"
                + utcDateTime.get(Calendar.MINUTE) + "===";
        System.out.println(output);
        // System.out.println(getSradTotal().getValue());
//call clculateAllStatistics()
        calculateStatistics();
// returns null
        return "";

    }

}
