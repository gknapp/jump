(ns jump.system.physics
  (:require [jump.entity :as ent]))

(def step 10)   ; jump / walk amount per frame
(def height 40) ; jump height
(def weight 10) ; gravitational pull

;; Filters

#_(defn falls?
  [entity]
  (let [has-position (ent/has-attr entity :position)
        has-weight (ent/has-attr entity :gravity)]
    ; need an ascending flag for jumping
    (and has-position has-weight)))

;; Actions

; walking

(defn walk?
  "is walk command?"
  [cmd]
  (#{:left :right} cmd))

(defn can-walk?
  "entity has a position, can walk and takes input commands"
  [entity]
  (and (ent/has-attr entity :position)
       (ent/has-attr entity :walk)
       (ent/has-attr entity :input)))

(defn walk
  [cmd entity]
  (if (can-walk? entity)
    (let [x (ent/attr entity [:position :x])
          op (if (= cmd :left) - +)
          update {:position {:x (op x step)}
                  :walk     {:facing cmd}}]
      (ent/update entity update))
    entity))

; jumping

(defn jump?
  "is jump command?"
  [cmd]
  (= cmd :jump))

(defn can-jump?
  [entity]
  (and (ent/has-attr entity :position)
       (ent/has-attr entity :jump)
       (ent/has-attr entity :input)
       (ent/attr entity [:jump :on-ground])))

(defn jump
  [entity]
  (if (can-jump? entity)
    (let [y (ent/attr entity [:position :y])
          update {:position {:y (- y step)}}]
      (ent/update entity update))
    entity))

(defn move
  [cmd game]
  (if-not (nil? cmd)
    (do
      #_(println "(phys/move)" cmd)
      (let [update (cond
                    (walk? cmd) (map #(walk cmd %) game)
                    (jump? cmd) (map jump game)
                    :else (println "(phys/move) unrecognised input:" cmd))]
        update))
    game))
