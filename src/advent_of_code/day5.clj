(ns advent-of-code.day5
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [advent-of-code.utils :refer [strings->ints split-comma]]))

(def input "0,9 -> 5,9\n8,0 -> 0,8\n9,4 -> 3,4\n2,2 -> 2,1\n7,0 -> 7,4\n6,4 -> 2,0\n0,9 -> 2,9\n3,4 -> 1,4\n0,0 -> 8,8\n5,5 -> 8,2")
(def input (slurp (io/resource "input-day5.txt")))

(defn line->coord-pair [line]
  (->> (str/split line #" -> ")
       (map (comp strings->ints split-comma))))

(defn parse-input []
  (->> (str/split-lines input)
       (map line->coord-pair)))

(def coord-pairs (parse-input))

(defn straight? [[[x1 y1] [x2 y2]]] (or (= x1 x2) (= y1 y2)))

(defn dangerous? [[_coord coverage]] (> coverage 1))

(defn ordinate-range [a b]
  (cond (> a b) (range a (dec b) -1)                        ; assumes diagonal
        (< a b) (range a (inc b))                           ; assumes diagonal
        :else (repeat a)))                                  ; horizontal or vertical

(defn coord-pair->line-of-coords [[[x1 y1] [x2 y2]]]
  (map vector (ordinate-range x1 x2) (ordinate-range y1 y2)))

(defn count-dangerous [pairs]
  (->> pairs
       (mapcat coord-pair->line-of-coords)
       frequencies
       (filter dangerous?)
       count))

(defn task1 [] (count-dangerous (filter straight? coord-pairs)))
(defn task2 [] (count-dangerous coord-pairs))