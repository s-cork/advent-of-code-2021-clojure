(ns advent-of-code.day2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2")
(def input (slurp (io/resource "input-day2.txt")))

(defn to-tuple [[direction amount]]
  [(keyword direction) (Integer/parseInt amount)])

(defn split-space [s] (str/split s #" "))

(defn input->tuples [input]
  (->> (str/split-lines input)
       (map (comp to-tuple split-space))))

(def init-pos {:x 0 :depth 0 :aim 0})

(def mapper-1 {:forward #(hash-map :x %) :down #(hash-map :depth %) :up #(hash-map :depth (- %))})
(def mapper-2 {:forward #(hash-map :x %) :down #(hash-map :aim %) :up #(hash-map :aim (- %))})

(defn make-mapper [mapper]
  (fn [[direction-key val]] ((direction-key mapper) val)))

(defn final-total [{:keys [x depth]}]
  (* x depth))

(defn solver [mapper merger]
  (fn []
    (->> (input->tuples input)
         (map (make-mapper mapper))
         (reduce merger init-pos)
         final-total)))

(defn merge-aim [prev nxt]
  (-> (merge-with + prev nxt)
      (update :depth + (* (:aim prev) (:x nxt 0)))))

(def task1 (solver mapper-1 (partial merge-with +)))
(def task2 (solver mapper-2 merge-aim))
