package com.meli.coupon;

import com.meli.coupon.repository.ItemsMeliRepositoryImplTest;
import com.meli.coupon.usecase.CalculateOptimalSolutionUseCaseTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		CalculateOptimalSolutionUseCaseTest.class,
		ItemsMeliRepositoryImplTest.class
})
class CouponApplicationTests {

}
