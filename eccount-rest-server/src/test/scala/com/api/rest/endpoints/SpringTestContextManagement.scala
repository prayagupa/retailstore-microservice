package com.api.rest.endpoints

/**
  * Created by prayagupd
  * on 1/29/17.
  */

import org.scalatest.{BeforeAndAfterAll, Suite}
import org.springframework.core.annotation.{AnnotatedElementUtils, AnnotationAttributes}
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.support.DirtiesContextTestExecutionListener
import org.springframework.test.context.{TestContext, TestContextManager}
import org.springframework.util.Assert

trait SpringTestContextManagement extends BeforeAndAfterAll { this: Suite =>

  private val testContextManager: TestContextManager = new TestContextManager(this.getClass)

  abstract override protected def beforeAll(): Unit = {
    super.beforeAll
    testContextManager.registerTestExecutionListeners(DirtiesForeverContextTEL)
    testContextManager.beforeTestClass
    testContextManager.prepareTestInstance(this)
  }

  abstract override protected def afterAll(): Unit = {
    testContextManager.afterTestClass
    super.afterAll
  }
}

protected object DirtiesForeverContextTEL extends DirtiesContextTestExecutionListener {

  @throws(classOf[Exception])
  override def afterTestClass(testContext: TestContext) {
    val testClass: Class[_] = testContext.getTestClass
    Assert.notNull(testClass, "The test class of the supplied TestContext must not be null")

    val annotationType: String = classOf[DirtiesContext].getName
    val annAttrs: AnnotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(testClass, annotationType)
    val hierarchyMode: DirtiesContext.HierarchyMode =
      if ((annAttrs == null)) null else annAttrs.getEnum[DirtiesContext.HierarchyMode]("hierarchyMode")
    dirtyContext(testContext, hierarchyMode)
  }
}
