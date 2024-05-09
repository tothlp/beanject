
import hu.tothlp.beanject.di.Beanject
import hu.tothlp.beanject.di.Beanject.bean
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BeanjectTest {

	@BeforeEach
	fun setup() {
		Beanject.beanContainer.clear()
	}

	@Test
	fun `register and retrieve bean by name`() {
		val mockBean: Any = mockk()
		Beanject.beans {
			bean("testBean") { mockBean }
		}

		val retrievedBean = Beanject.getBeanByName("testBean")
		assertEquals(mockBean, retrievedBean)
	}

	@Test
	fun `register and retrieve bean by type`() {
		val mockBean: String = "Test String"
		Beanject.beans {
			bean("testBean") { mockBean }
		}

		val retrievedBean = Beanject.getBean<String>()
		assertEquals(mockBean, retrievedBean)
	}

	@Test
	fun `retrieve non-existent bean by name throws exception`() {
		assertThrows<IllegalArgumentException> {
			Beanject.getBeanByName("nonExistentBean")
		}
	}

	@Test
	fun `retrieve non-existent bean by type throws exception`() {
		assertThrows<IllegalArgumentException> {
			Beanject.getBean<String>()
		}
	}

	@Test
	fun `register multiple beans and retrieve by type`() {
		val mockBean1: String = "Test String 1"
		val mockBean2: String = "Test String 2"
		Beanject.beans {
			bean("testBean1") { mockBean1 }
			bean("testBean2") { mockBean2 }
		}

		val retrievedBean = Beanject.getBean<String>()
		assertTrue(retrievedBean == mockBean1 || retrievedBean == mockBean2)
	}
}