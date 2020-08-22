package org.cab.admin.anno;

import javax.validation.Valid;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckParam {
}
