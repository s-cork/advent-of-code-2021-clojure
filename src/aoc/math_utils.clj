(ns aoc.math-utils
  (:require [clojure.math.numeric-tower :as clj-math]))

(defn tri-num [n]
  (/ (* n (inc n)) 2))

(def abs clj-math/abs)
(def sqrt clj-math/sqrt)

(defn sqr
  "Uses the numeric tower expt to square a number"
  [x]
  (clj-math/expt x 2))

(defn- euclidean-squared-distance
  "Computes the Euclidean squared distance between two sequences"
  [a b]
  (reduce + (map (comp sqr -) a b)))

(defn euclidean-distance
  "Computes the Euclidean distance between two sequences"
  [a b]
  (sqrt (euclidean-squared-distance a b)))

(comment
  (let [a [1 2 3 5 8 13 21]
        b [0 2 4 6 8 10 12]]
    (euclidean-distance a b))
  ;
  )