package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(strict = false)
data class Platform(@get:Attribute(name="Number") @set:Attribute(name="Number") var platform: Int? = null)