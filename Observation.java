/**
 * Observation class has a constructor that takes in a double and a string. It
 * checks if a value is valid and returns the station IDs.
 * 
 * @author apotsangyangzom
 * @version 2018-10-25
 */
public class Observation extends AbstractObservation
{

    /**
     * The value from the data in the file
     */
    private double value;
    /**
     * To check if the value if valid or not.
     */
    private boolean valid;
    /**
     * Gives the stations id.
     * 
     */
    private String stid;

    /**
     * An observation constructor that takes in the values and the station id.
     * 
     * @param value The maximum, minimum, average and total values for srad, tair,
     *              ta9m.
     * @param stid  which is the station id.
     */

    public Observation(double value, String stid)
    {
        //assigns the value to this value
        this.value = value;
        //assigns the stid to this stid.
        this.stid = stid;

    }

    public double getValue()
    {

        return this.value;
    }

    /**
     * Checks if the values are valid or not. If they are below the smallest value
     * in the whole file or larger than the largest value, returns invalid.
     * 
     * @return true if a value is valid. False if invalid.
     */

    public boolean isValid()
    {
        //checks if a value is valid
        if (value < -900)
        {
            valid = false;
        }
        else
        {
            valid = true;
        }
        return this.valid;
    }

    /**
     * Gives the station id.
     * 
     * @return stid which is the name of the station id.
     */

    public String getStid()
    {
        //returns this stid
        return this.stid;
    }

    public String toString()
    {
        return stid;
    }

}
