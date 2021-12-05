(ns advent-of-code.day5-ext
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [advent-of-code.utils :refer [re-seq-ints gcd]]))
;;;; Extension
;;;  what if it wasn't 45 degrees?
;; (def input "0,9 -> 5,9\n8,0 -> 0,8\n9,4 -> 3,4\n2,2 -> 2,1\n7,0 -> 7,4\n6,4 -> 2,0\n0,9 -> 2,9\n3,4 -> 1,4\n0,0 -> 8,8\n5,5 -> 8,2")
(def input (slurp (io/resource "input-day5.txt")))

(def coord-pairs (->> (str/split-lines input)
                      (map re-seq-ints)))

(defn h-or-v? [[x1 y1 x2 y2]] (or (= x1 x2) (= y1 y2)))

(defn dangerous? [[_coord coverage]] (> coverage 1))

(defn get-step [[x1 y1 x2 y2]]
  (let [diffs [(- x2 x1) (- y2 y1)]
        diff-gcd (apply gcd diffs)]
    (map #(abs (/ % diff-gcd)) diffs)))

(defn ordinate-range [a b step]
  (cond (> a b) (range a (dec b) (- step))                  ; assumes diagonal
        (< a b) (range a (inc b) step)                      ; assumes diagonal
        :else (repeat a)))                                  ; horizontal or vertical

(defn coord-pair->coord-path [coord]
  (let [[step1 step2] (get-step coord) [x1 y1 x2 y2] coord]
    (map vector (ordinate-range x1 x2 step1) (ordinate-range y1 y2 step2))))

(defn count-dangerous [pairs]
  (->> pairs
       (mapcat coord-pair->coord-path)
       frequencies
       (filter dangerous?)
       count))

(defn task1 [] (count-dangerous (filter h-or-v? coord-pairs)))
(defn task2 [] (count-dangerous coord-pairs))
