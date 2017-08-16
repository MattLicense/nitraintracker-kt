package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name="Origin1", strict=false)
data class Origin(@get:Attribute @set:Attribute var name: String? = null)