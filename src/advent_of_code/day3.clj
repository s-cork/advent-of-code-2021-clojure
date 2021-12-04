(ns advent-of-code.day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input "00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010")
;;(def input (slurp (io/resource "input-day3.txt")))

(def bin-strs (str/split-lines input))
(defn bin->int [s] (Integer/parseInt s 2))

(defn transpose [m]
  (apply mapv str m))

(defn filter-by-index [coll idx]
  (map (partial nth coll) idx))

(defn ones-zeros [bin-str]
  (let [total (count bin-str)
        ones (count (filter #{\1} bin-str))
        zeros (- total ones)]
    [ones zeros]))

(defn decide-most-common [decider bin-str]
  (let [[ones zeros] (ones-zeros bin-str)]
    (cond (> ones zeros) (:one decider)
          (< ones zeros) (:zero decider)
          :else (:tie decider))))

(defn task1 []
  (->> bin-strs
       transpose
       (map (partial decide-most-common {:one [\1 \0] :zero [\0 \1] :tie [\1 \0]}))
       transpose
       (map bin->int)
       (apply *)))

(defn reduce-indices
  [indices bin-str decider]
  (let [bin-chars (filter-by-index bin-str indices)
        most-common (decide-most-common decider bin-chars)]
    (filter identity (map #(and (= %1 most-common) %2) bin-chars indices))))

(defn get-initial-indices [transposed]
  (range (.length (first transposed))))

(defn get-index [transposed decider]
  (loop [indices (get-initial-indices transposed)
         n 0]
    (if-not (seq (next indices))
      (first indices)
      (recur (reduce-indices indices (nth transposed n) decider)
             (inc n)))))

(def decider-o2 {:one \1 :zero \0 :tie \1})
(def decider-co2 {:one \0 :zero \1 :tie \0})

(defn task2 []
  (let [transposed (transpose bin-strs)]
    (->> [decider-o2 decider-co2]
         (map (comp bin->int                                ;; [23 10]
                    (partial nth bin-strs)                  ;; ["10111" "01010"]
                    (partial get-index transposed)))        ;; [3 11]
         (apply *))))                                       ;; 230
