/*
 * The MIT License
 *
 * Copyright (c) 2019 Daniel Beck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jenkins;

import hudson.ExtensionList;
import org.kohsuke.MetaInfServices;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.LocaleDrivenResourceProvider;

import java.net.URL;

@Restricted(NoExternalUse.class)
@MetaInfServices
public final class MetaLocaleDrivenResourceProvider extends LocaleDrivenResourceProvider {
    @Override
    public URL lookup(String s) {
        /*
         * This looks crazy but this instance only registers as MetaInfService so it can be looked up by Stapler.
         * All other instances (in plugins) are expected to register as @Extension, making them available below.
         *
         * This saves having to think of one more stupid class name. The downside is the lack of "implements ExtensionPoint".
         */
        for (LocaleDrivenResourceProvider provider : ExtensionList.lookup(LocaleDrivenResourceProvider.class)) {
            URL url = provider.lookup(s);
            if (url != null) {
                return url;
            }
        }
        return null;
    }
}
