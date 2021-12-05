(ns advent-of-code.utils
  (:require [clojure.string :as str]))

(defn split-multi-lines [s]
  (str/split (str/trim s) #"\n{2,}"))

(defn str->int [s]
  (Integer/parseInt s))

(defn strings->ints [coll]
  (map str->int coll))

(defn bin-str->int [s]
  (Integer/parseInt s 2))

(defn split-white-space [s]
  (str/split (str/trim s) #"\s+"))

(defn split-comma [s]
  (str/split s #","))

(defn re-seq-ints [s]
  (strings->ints (re-seq #"\d+" s)))

(defn abs [n]
  (if (neg? n) (- n) n))

(defn gcd [a b]
  (if (zero? b)
    (abs a)
    (recur b (mod a b))))
