(ns advent-of-code.day2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2")
(def input (slurp (io/resource "input-day2.txt")))

(defn to-tuple [[direction amount]]
  [(keyword direction) (Integer/parseInt amount)])

(defn split-space [s] (str/split s #" "))

(def init-pos {:x 0 :depth 0 :aim 0})

(defn to-instructions-1 [[direction-key val]]
  (case direction-key
    :forward {:x val}
    :down {:depth val}
    :up {:depth (- val)}))

(defn to-instructions-2 [[direction-key val]]
  (case direction-key
    :forward {:x val}
    :down {:aim val}
    :up {:aim (- val)}))

(defn final-total [{:keys [x depth]}]
  (* x depth))

(defn merge-with-aim [state {:keys [x aim] :as _instruction}]
  (if aim (update state :aim + aim)
      (-> state
          (update :x + x)
          (update :depth + (* (:aim state) x)))))

(defn solver [to-instruction merger]
  (fn []
    (->> input
         str/split-lines
         (map (comp to-instruction
                    to-tuple
                    split-space))
         (reduce merger init-pos)
         final-total)))

(def task1 (solver to-instructions-1 (partial merge-with +)))
(def task2 (solver to-instructions-2 merge-with-aim))
