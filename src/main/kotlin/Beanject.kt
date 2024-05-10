package hu.tothlp.beanject.di

object Beanject {
	private val _beanContainer = mutableMapOf<String, Any>()
	val beanContainer: Map<String, Any> get() = _beanContainer

	fun getBeanByName(name: String): Any? {
		return _beanContainer[name] ?: throw IllegalArgumentException("No bean found with name $name")
	}

	inline fun <reified T> getBean(name: String? = null): T {
		if (beanContainer.none { it.value is T }) throw IllegalArgumentException("No bean found for type ${T::class.simpleName}")
		return if (!name.isNullOrBlank()) getBeanByName(name) as T
		else beanContainer.values.first { it is T } as T
	}

	private fun registerBean(name: String, bean: Any) {
		_beanContainer[name] = bean
	}

	fun clear() {
		_beanContainer.clear()
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

