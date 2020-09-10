import java.util.GregorianCalendar;
/**
 * DateTimeComparable Class
 * @author apotsangyangzom
 *
 */
interface DateTimeComparable
{
    boolean newerThan(GregorianCalendar inDateTimeUTC);

    boolean olderThan(GregorianCalendar inDateTimeUTC);

    boolean sameAs(GregorianCalendar inDateTimeUTC);

}
