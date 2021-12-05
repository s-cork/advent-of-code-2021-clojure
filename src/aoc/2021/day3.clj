(ns aoc.2021.day3
  (:require [aoc.string-utils :refer [bin-str->int]]
            [aoc.file-utils :as file-utils]))

;;(def bin-strs (str/split-lines "00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010"))
(def bin-strs (file-utils/read-lines "2021/day3.txt"))

(defn transpose [m]
  (apply mapv str m))

(defn filter-by-index [coll idx]
  (map (partial nth coll) idx))

(defn zero-wins? [transposed-bit]
  (= \0 (ffirst (sort-by val (frequencies (seq transposed-bit))))))

(defn decide-most-common [decider transposed-bit]
  (if (zero-wins? transposed-bit) (:zero decider) (:one decider)))

(defn reduce-indices
  [indices transposed-bit decider]
  (let [bin-chars (filter-by-index transposed-bit indices)
        most-common (decide-most-common decider bin-chars)]
    (reduce (fn [result [chr index]]
              (if (#{most-common} chr) (conj result index) result))
            []
            (map vector bin-chars indices))))

(defn get-initial-indices [transposed]
  (range (.length (first transposed))))

(defn get-index [decider transposed]
  (first (reduce (fn [indices transposed-bit]
                   (if (= 1 (count indices))
                     (reduced indices)
                     (reduce-indices indices transposed-bit decider)))
                 (get-initial-indices transposed)
                 transposed)))

(defn get-final-bit-1 [_original decider transposed]
  (->> (map (partial decide-most-common decider) transposed)
       (apply str)))

(defn get-final-bit-2 [original decider transposed]
  (->> (get-index decider transposed)
       (nth original)))

(defn solve [& get-final-bit-fns]
  (->> (transpose bin-strs)
       ((apply juxt get-final-bit-fns))
       (map bin-str->int)
       (apply *)))

(defn task1 []
  (solve (partial get-final-bit-1 bin-strs {:one \1 :zero \0})
         (partial get-final-bit-1 bin-strs {:one \0 :zero \1})))

(defn task2 []
  (solve (partial get-final-bit-2 bin-strs {:one \1 :zero \0})
         (partial get-final-bit-2 bin-strs {:one \0 :zero \1})))
