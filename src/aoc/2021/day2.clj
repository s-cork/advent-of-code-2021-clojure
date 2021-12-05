(ns aoc.2021.day2
  (:require [clojure.string :as str]
            [aoc.file-utils :as file-utils]))

;;(def input-lines (str/split-lines "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2"))
(def input-lines (file-utils/read-lines "2021/day2.txt"))

(defn line->tuple [line]
  (let [[direction amount] (str/split line " ")]
    [(keyword direction) (Integer/parseInt amount)]))

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
  (->> input-lines
       (map line->tuple)
       (reduce merger {:x 0 :depth 0 :aim 0})
       final-total))

(defn task1 [] (solve merger-1))
(defn task2 [] (solve merger-2))
