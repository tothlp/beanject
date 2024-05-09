package hu.tothlp.beanject.di

object Beanject {

	val beanContainer = mutableMapOf<String, Any>()

	fun getBeanByName(name: String): Any? {
		return beanContainer[name] ?: throw IllegalArgumentException("No bean found with name $name")
	}

	inline fun <reified T> getBean(name: String? = null): T {
		if (beanContainer.none { it.value is T }) throw IllegalArgumentException("No bean found for type ${T::class.simpleName}")
		return if (!name.isNullOrBlank()) getBeanByName(name) as T
		else beanContainer.values.first { it is T } as T
	}

	private fun registerBean(name: String, bean: Any) {
		beanContainer[name] = bean
	}

	class BeanDefinitionDsl()

	fun BeanDefinitionDsl.bean(name: String, beanCreator: BeanDefinitionDsl.() -> Any): Any {
		val bean = beanCreator()
		registerBean(name, bean)
		return bean
	}

	fun beans(init: BeanDefinitionDsl.() -> Unit): BeanDefinitionDsl {
		val beans = BeanDefinitionDsl()
		beans.init()
		return beans
	}
}