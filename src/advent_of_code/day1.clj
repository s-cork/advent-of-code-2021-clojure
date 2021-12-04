(ns advent-of-code.day1
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input "199\n200\n208\n210\n200\n207\n240\n269\n260\n263\n") ;; task1 7, task2 5
(def input (slurp (io/resource "input-day1.txt")))

(def input-as-nums (->> (str/split-lines input)
                        (map #(Integer/parseInt %))))

(defn windows [nums]
  (map + nums (drop 1 nums) (drop 2 nums)))

(defn solver [nums]
  (->> (apply map > (drop 1 nums) nums)
       (filter true?)
       count))

(defn task1 [] (solver input-as-nums))

(defn task2 [] (solver (windows input-as-nums)))