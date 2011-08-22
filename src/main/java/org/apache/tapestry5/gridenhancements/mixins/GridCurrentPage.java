package org.apache.tapestry5.gridenhancements.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;

public class GridCurrentPage
{
    @Parameter(value = "true", allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private boolean includeId;

    private static final String PAGE_PARAM = "page";

    @InjectContainer
    private Grid grid;

    @Inject
    private ComponentResources resources;

    @Inject
    private Request request;

    @Inject
    private PageRenderLinkSource linkSource;

    /**
     * This logic should be executed before setupDataSource called in the Grid setupRender phase, because it updates the
     * currentPage of the Grid.
     */
    void beginRender()
    {
        String pageParam = request.getParameter(getPageParameterName());
        if (pageParam == null) return;

        try
        {
            grid.setCurrentPage(Integer.valueOf(String.valueOf(pageParam)));
        }
        catch (NumberFormatException exception)
        {
        }
    }

    private String getPageParameterName()
    {
        String gridId = resources.getContainer().getComponentResources().getId();

        String paramName;
        if (includeId)
            paramName = String.format("%s.%s", gridId, PAGE_PARAM);
        else
            paramName = String.format("%s", PAGE_PARAM);

        return paramName;
    }

    /**
     * Non Ajax event handler for the {@link org.apache.tapestry5.corelib.components.GridPager}.
     * <p>
     * The side effect of using this mixin is that the {@link org.apache.tapestry5.corelib.components.GridPager} action
     * event will not be propagated any further. The event will stop here.
     */
    Object onActionFromPager(int newPage)
    {
        Link link = linkSource.createPageRenderLink(resources.getPageName());

        link.addParameter(getPageParameterName(), Integer.toString(newPage));

        return link;
    }
}
