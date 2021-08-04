package lv.zesloka.domain.contract
import lv.zesloka.domain.model.validators.AllCool
import lv.zesloka.domain.model.validators.Decision

/**
 * A composable object validator chain.
 * If one of validators fail, chain returns failed validator.
 *
 * Abstract example:
 * val decision = IsSunIsShining(input = weatherForecast)
 *      .and(IsWeekend(input = remoteCalendar))
 *      .and(IsFreeTime(input = mySchedule))
 *      .validate()
 *
 *  when(decision) {
 *      is AllCool -> {
 *          while(isFun) {
 *              isFun = takeABeer()
 *          }
 *      }
 *      is NotCool -> {
 *          when(decision.problem) {
 *              is IsSunIsShining -> {
 *                  screwIt()
 *                  takeABer()
 *              }
 *              is IsWeekend, isFreeTime -> {
 *                  findATime()
 *                  takeABeer()
 *              }
 *          }
 *      }
 *  }
 *
 *  Warning: Actual use-cases is much more boring.
 */
abstract class Validator {

    private var next: Validator? = null

    fun and(validator: Validator): Validator {
        validator.next = this
        return validator
    }

    suspend fun validate(): Decision {
        return if (next != null) {
            val result = next?.getValidationResult()!!

            if (result is AllCool) {
                getValidationResult()
            } else {
                result
            }
        } else {
            getValidationResult()
        }
    }

    protected abstract suspend fun getValidationResult(): Decision
}











