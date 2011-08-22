// Copyright 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.gridenhancements.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.PAGE;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ioc.annotations.UseWith;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A marker annotation that indicates that all the request parameters will be added to the page render links and/or
 * component event links in the annotated page, using the {@link EventConstants#DECORATE_PAGE_RENDER_LINK} and/or
 * {@link EventConstants#DECORATE_COMPONENT_EVENT_LINK} event handlers accordingly.
 * <p>
 * Decorating component and/or page render links is controlled by the {@link #pageRenderLinks()} and
 * {@link #componentEventLinks()} parameters.
 * <p>
 * Additionally the components on which the component event links are to be decorated can be selected using the
 * {@link #components()} parameter.
 * 
 * @since 5.3
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@UseWith(PAGE)
public @interface KeepRequestParameters
{
    /**
     * Weather to decorate all page render links in the page
     */
    boolean pageRenderLinks() default true;

    /**
     * Weather to decorate all component event links in the page
     */
    boolean componentEventLinks() default true;

    /**
     * The components on which the event links will be decorated. The components are identified by their componentId
     * relative to the page example: grid, grid.rows, grid.rows.gridcell etc. The value for a component is a regular
     * expression for example: grid* will match all the components in the page with t:id starting with grid and their
     * subcomponents.
     * <p>
     * Leaving the field blank (default) means that the events on all components will be decorated.
     */
    String[] components() default {};
}