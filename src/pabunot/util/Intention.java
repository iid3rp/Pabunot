package pabunot.util;
import java.lang.annotation.*;
/**
 * Annotation to mark code elements that should not be altered without permission.
 * It indicates intentional design choices that are crucial for specific reasons.
 * Modifications require prior approval due to potential risks and implications.
 *
 * @author Francis (iid3rp) Madanlo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.LOCAL_VARIABLE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface Intention 
{
    boolean isPublic() default true; // stating the publicity of the field / method
    String design() default ""; // what's the design of that field, the functionality
    String reason() default ""; // the reason why is it like that..
}