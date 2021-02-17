package com.waxym.utils.injection

import com.waxym.utils.exception.InjectionException
import kotlin.reflect.KClass


object Bob {
    private val components = hashMapOf<KClass<*>, Any>()

    operator fun <I : Any, O : I> set(clazz: KClass<I>, component: O) {
        components[clazz] = component
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(clazz: KClass<*>): T {
        return components[clazz] as? T ?: throw InjectionException(clazz)
    }
}

inline fun <reified T> Any.get(): T = Bob[T::class]

inline fun <reified T> Any.inject(): Lazy<T> = lazy { Bob[T::class] }
