(ns aoc.2021.day6-alt
  (:require [aoc.string-utils :refer [str->ints]]
            [aoc.file-utils :as file-utils]))

(def init-fishes (str->ints "3,4,3,1,2"))
;;(def init-fishes (file-utils/read-ints "2021/day6.txt"))

(def no-fish-counters (into {} (map vector (range 9) (repeat 0))))

(def init-fish-counters (merge no-fish-counters (frequencies init-fishes)))

(defn young-fish? [fish-counter] (> fish-counter 6))
(def baby-counter 8)

(defn dec-counters [fish-counters]
  (reduce-kv (fn [m fish-counter num-fish]
               (if (young-fish? fish-counter)
                 (update m (dec fish-counter) + num-fish)
                 (update m (mod (dec fish-counter) 7) + num-fish)))
             no-fish-counters
             fish-counters))

(defn get-births [fish-counter] (fish-counter 0))

(defn gen-fish [fish-counters]
  (let [baby-gen (get-births fish-counters)]
    (-> (dec-counters fish-counters)
        (assoc baby-counter baby-gen))))

(defn get-nth-count [n]
  (as-> init-fish-counters $
        (iterate gen-fish $)
        (nth $ n)
        (vals $)
        (apply + $)))

(comment
  (get-nth-count 80)
  (get-nth-count 256)
  ;
  )