// Copyright 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.gridenhancements.integration.pages;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.gridenhancements.integration.data.Track;
import org.apache.tapestry5.gridenhancements.integration.services.MusicLibrary;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class GridCurrentPageAndColumnSortMixinDemo
{
    @Inject
    private MusicLibrary library;

    @Component
    private Grid grid1;

    @Component
    private Grid grid2;

    public List<Track> getTracks()
    {
        return library.getTracks();
    }

    void onActionFromReset1()
    {
        grid1.reset();
    }

    void onActionFromReset2()
    {
        grid2.reset();
    }

    @Inject
    private PageRenderLinkSource linkSource;

    Link onActionFromGotoPage2()
    {
        Link link = linkSource.createPageRenderLink(GridCurrentPageAndColumnSortMixinDemo.class);

        link.addParameter("page", "2");

        return link;
    }

    Link onActionFromGotoGrid1Page2Grid2Page3()
    {
        Link link = linkSource.createPageRenderLink(GridCurrentPageAndColumnSortMixinDemo.class);

        link.addParameter("page", "2");
        link.addParameter("grid2.page", "3");

        return link;
    }
    
    Link onActionFromSortGrid1OnAlbum()
    {
        Link link = linkSource.createPageRenderLink(GridCurrentPageAndColumnSortMixinDemo.class);

        link.addParameter("sort", "album");
        link.addParameter("sortorder", "asc");

        return link;
    }
    
    Link onActionFromSortGrid1OnAlbumSortGrid2OnArtist()
    {
        Link link = linkSource.createPageRenderLink(GridCurrentPageAndColumnSortMixinDemo.class);

        link.addParameter("sort", "album");
        link.addParameter("sortorder", "asc");
        
        link.addParameter("grid2.sort", "artist");
        link.addParameter("grid2.sortorder", "asc");

        return link;
    }

}
