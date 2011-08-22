package org.apache.tapestry5.gridenhancements.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;

public class GridColumnSort
{
    @Parameter(value = "true", allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private boolean includeId;

    private static final String SORT_PARAM = "sort";

    private static final String ORDER_PARAM = "sortorder";

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
     * SortModel of the Grid.
     */
    void beginRender()
    {
        String sortColumn = request.getParameter(getSortColumnParameterName());

        if (sortColumn == null) return;

        String sortOrder = request.getParameter(getSortOrderParameterName());

        GridModel gridModel = (GridModel) grid;

        ColumnSort columnSort = gridModel.getSortModel().getColumnSort(sortColumn);
        if (sortOrder == null) return;

        if (sortOrder.equals("asc") && columnSort == ColumnSort.ASCENDING) return;
        if (sortOrder.equals("desc") && columnSort == ColumnSort.DESCENDING) return;

        if (sortOrder.equals("desc") || sortOrder.equals("asc"))
            if (gridModel.getDataModel().getPropertyNames().contains(sortColumn))
                gridModel.getSortModel().updateSort(sortColumn);

    }

    private String getSortColumnParameterName()
    {
        String gridId = resources.getContainer().getComponentResources().getId();
        String paramName;
        if (includeId)
            paramName = String.format("%s.%s", gridId, SORT_PARAM);
        else
            paramName = String.format("%s", SORT_PARAM);

        return paramName;
    }

    private String getSortOrderParameterName()
    {
        String gridId = resources.getContainer().getComponentResources().getId();
        String paramName;
        if (includeId)
            paramName = String.format("%s.%s", gridId, ORDER_PARAM);
        else
            paramName = String.format("%s", ORDER_PARAM);

        return paramName;
    }

    /**
     * Non Ajax event handler for the {@link org.apache.tapestry5.corelib.components.GridColumns} sort event. The event
     * handler in GridColumns is void which means the event will propagate in mixins and containing component. This
     * event handler adds parameters about the sort column and sort order to a link pointing to the current page.
     * <p>
     * The side effect of using this mixin is that the {@link org.apache.tapestry5.corelib.components.GridColumns} sort
     * event will not be propagated any further. The event will stop here.
     */
    Object onSort(String columnId)
    {
        Link link = linkSource.createPageRenderLink(resources.getPageName());

        link.addParameter(getSortColumnParameterName(), columnId);
        ColumnSort columnSort = ((GridModel) grid).getSortModel().getColumnSort(columnId);
        String colSort = "asc";
        if (columnSort == ColumnSort.DESCENDING) colSort = "desc";
        link.addParameter(getSortOrderParameterName(), colSort);

        return link;
    }
}
