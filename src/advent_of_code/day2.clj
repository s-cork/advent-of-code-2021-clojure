(ns advent-of-code.day2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2")
(def input (slurp (io/resource "input-day2.txt")))

(defn to-tuple [[direction amount]]
  [(keyword direction) (Integer/parseInt amount)])

(defn split-space [s] (str/split s #" "))

(defn merger-1 [state [direction-key val]]
  (case direction-key
    :up (update state :depth - val)
    :down (update state :depth + val)
    :forward (update state :x + val)))

(defn merger-2 [state [direction-key val]]
  (case direction-key
    :up (update state :aim - val)
    :down (update state :aim + val)
    :forward (-> state
                 (update :x + val)
                 (update :depth + (* (:aim state) val)))))

(defn final-total [{:keys [x depth]}]
  (* x depth))

(defn solve [merger]
  (->> input
       str/split-lines
       (map (comp to-tuple split-space))
       (reduce merger {:x 0 :depth 0 :aim 0})
       final-total))

(defn task1 [] (solve merger-1))
(defn task2 [] (solve merger-2))
