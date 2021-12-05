(ns advent-of-code.day4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [advent-of-code.utils :refer :all]))

(def input "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n\n22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19\n\n 3 15  0  2 22\n 9 18 13 17  5\n19  8  7 25 23\n20 11 10 24  4\n14 21 16 12  6\n\n14 21 17 24  4\n10 16 15  9 19\n18  8 23 26 20\n22 11 13  6  5\n 2  0 12  3  7\n")
;;(def input (slurp (io/resource "input-day4.txt")))

;;;; HELPERS to get state from the input
(defn pos-val->board-piece [pos val]
  {:val val :marked false :row (quot pos 5) :col (rem pos 5)})

(defn get-board-pieces [lines]
  (->> (re-seq-ints lines)
       (map-indexed pos-val->board-piece)))

(defn get-init-state []
  (let [lines (split-multi-lines input)]
    {:draw    (re-seq-ints (first lines))
     :boards  (map get-board-pieces (rest lines))
     :win     nil
     :current nil}))

(def init-state (get-init-state))

;;;; Mark Cards
(defn mark-piece [{current :current} {val :val :as piece}]
  (if (= current val) (assoc piece :marked true) piece))

(defn mark-board [state board]
  (map (partial mark-piece state) board))

(defn mark-boards [{boards :boards :as state}]
  (assoc state :boards (map (partial mark-board state) boards)))

;;;; Check For Winning Boards
(defn get-marked-piece [{current :current} board]
  (first (filter (comp #{current} :val) board)))

(defn get-rows [row board] (filter (comp #{row} :row) board))
(defn get-cols [col board] (filter (comp #{col} :col) board))

(defn check-win [state board]
  (when-let [piece (get-marked-piece state board)]
    (or (every? :marked (get-rows (:row piece) board))
        (every? :marked (get-cols (:col piece) board)))))

(defn board-to-win [{boards :boards :as state} board]
  (merge state
         {:boards (remove (partial identical? board) boards) :win board}))

(defn board-to-win-reducer [state board]
  (if (check-win state board) (board-to-win state board) state))

(defn check-wins
  "loop over the boards - if a board is a winning board, move it from :boards to :win"
  [{boards :boards :as state}]
  (reduce board-to-win-reducer state boards))

;;;; Main Loop Fns
(defn draw-card [{draw :draw :as state}]
  (merge state {:draw (rest draw) :current (first draw)}))

(defn bingo-round [state]
  (-> state
      draw-card
      mark-boards
      check-wins))

(defn sum-unmarked [board]
  (->> board
       (remove :marked)
       (map :val)
       (apply +)))

(defn bingo [{:keys [current win]}]
  (let [unmarked-sum (sum-unmarked win)]
    (println current unmarked-sum)
    (* current unmarked-sum)))

;;;; Tasks
(defn play-bingo [end-game?]
  (loop [state init-state]
    (if (end-game? state)
      (bingo state)
      (recur (bingo-round state)))))

(defn task1 [] (play-bingo :win))
(defn task2 [] (play-bingo (comp empty? :boards)))
