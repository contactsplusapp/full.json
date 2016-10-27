(ns full.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [full.json-test]))

(doo-tests 'full.json-test)
