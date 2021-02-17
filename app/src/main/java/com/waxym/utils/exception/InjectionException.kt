package com.waxym.utils.exception

import kotlin.reflect.KClass

class InjectionException(clazz: KClass<*>) : RuntimeException("InjectionException occur for class:${clazz}")