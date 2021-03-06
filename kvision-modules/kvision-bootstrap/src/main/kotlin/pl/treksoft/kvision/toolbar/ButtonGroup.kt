/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pl.treksoft.kvision.toolbar

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.px

/**
 * Button group sizes.
 */
enum class ButtonGroupSize(internal val className: String) {
    LARGE("btn-group-lg"),
    SMALL("btn-group-sm")
}

/**
 * The Bootstrap button group.
 *
 * @constructor
 * @param size button group size
 * @param vertical determines if button group is aligned vertically
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class ButtonGroup(
    size: ButtonGroupSize? = null, vertical: Boolean = false,
    classes: Set<String> = setOf(), init: (ButtonGroup.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * Button group size.
     */
    var size by refreshOnUpdate(size)
    /**
     * Vertical alignment.
     */
    var vertical by refreshOnUpdate(vertical)

    init {
        role = "group"
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (vertical) {
            cl.add("btn-group-vertical" to true)
        } else {
            cl.add("btn-group" to true)
        }
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.buttonGroup(
    size: ButtonGroupSize? = null, vertical: Boolean = false,
    classes: Set<String> = setOf(), init: (ButtonGroup.() -> Unit)? = null
): ButtonGroup {
    val group = ButtonGroup(size, vertical, classes).apply { init?.invoke(this) }
    this.add(group)
    return group
}

/**
 * DSL builder extension function for toolbar.
 *
 * It creates button groups with size and vertical parameters of the toolbar.
 */
fun Toolbar.buttonGroup(
    classes: Set<String> = setOf(), init: (ButtonGroup.() -> Unit)? = null
): ButtonGroup {
    val group = ButtonGroup(this.size, this.vertical, classes).apply {
        marginRight = this@buttonGroup.spacing.px
        init?.invoke(this)
    }
    this.add(group)
    return group
}
