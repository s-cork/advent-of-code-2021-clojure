(ns aoc.math-utils)

(defn abs [n]
  (if (neg? n) (- n) n))

(defn gcd [a b]
  (if (zero? b)
    (abs a)
    (recur b (mod a b))))
