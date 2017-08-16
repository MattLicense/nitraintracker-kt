package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "ServiceType", strict = false)
data class ServiceType(@get:Attribute(name = "Type", required = false) @set:Attribute(name = "Type", required = false) var type: String? = "")