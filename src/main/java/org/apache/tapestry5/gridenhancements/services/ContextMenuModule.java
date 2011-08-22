package org.apache.tapestry5.gridenhancements.services;

import org.apache.tapestry5.gridenhancements.internal.KeepRequestParametersWorker;
import org.apache.tapestry5.internal.services.ComponentClassCache;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

public class ContextMenuModule
{
    @Contribute(ComponentClassResolver.class)
    public void provideComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("core", "org.apache.tapestry5.gridenhancements"));
    }

    @Contribute(ComponentClassTransformWorker2.class)
    @Primary
    public static void provideKeepRequestParametersWorker(
            OrderedConfiguration<ComponentClassTransformWorker2> configuration, Request request,
            ComponentClassCache classCache, ValueEncoderSource valueEncoderSource)
    {
        configuration.add("KeepRequestParametersWorker", new KeepRequestParametersWorker(request,
                classCache, valueEncoderSource));
    }
}
