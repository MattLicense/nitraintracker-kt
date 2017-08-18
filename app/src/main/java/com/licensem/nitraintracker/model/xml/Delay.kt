package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "Delay", strict = false)
data class Delay(@get:Attribute(name = "Minutes") @set:Attribute(name = "Minutes") var delayMinutes: String? = "0")