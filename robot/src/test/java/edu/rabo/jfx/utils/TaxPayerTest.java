package edu.rabo.jfx.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TaxPayerTest {

	final static long income = 10000000;
	private static final Person person = new Person(income);
	private static final Trust nonProfit = new Trust(income, true);
	private static final Trust forProfit = new Trust(income, false);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testModels() {
		Assert.assertEquals(person.income, income);
		Assert.assertEquals(nonProfit.income, income);
		Assert.assertTrue(nonProfit.isNonProfit());
		Assert.assertEquals(forProfit.income, income);
		Assert.assertFalse(forProfit.isNonProfit());
	}
	
	@Test
	public void testStrategies() {
		final TaxStrategy<Person> defaultStrategy = new DefaultTaxStrategy<Person>();
		final TaxStrategy<Person> dodgingStrategy = new DodgingTaxStrategy<Person>();
		final TaxStrategy<Trust> trustStrategy = new TrustTaxStrategy();
		
		Assert.assertTrue(defaultStrategy.computeTax(person) == 4000000);		
		Assert.assertTrue(dodgingStrategy.computeTax(person) == 0);
		Assert.assertTrue(trustStrategy.computeTax(nonProfit) == 0);
		Assert.assertTrue(trustStrategy.computeTax(forProfit) == 4000000);
	}
}
