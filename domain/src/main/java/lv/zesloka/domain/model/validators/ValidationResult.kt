package lv.zesloka.domain.model.validators

import lv.zesloka.domain.contract.Validator

interface Decision
class AllCool: Decision
class NotCool(val problem: Validator):
    Decision