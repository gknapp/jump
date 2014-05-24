(ns jump.system.physics
  (:require [jump.entity :as ent]))

(def gravity 0.5) ; gravitational pull
(def step 10)     ; jump / walk amount per frame
(def height 60)   ; max jump height
(def weight 10)   ; gravitational pull

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
  [cmd entity]
  (if (can-jump? entity)
    (if (= cmd [:jump :start])
      (let [y (ent/attr entity [:position :y])
            update {:position {:y (- y step)}}]
        (ent/update entity update))
      #_(else))
    entity))

(defn apply-command
  [cmd game]
  (cond
   (walk? cmd) (map #(walk cmd %) game)
   (jump? cmd) (map #(jump cmd %) game)
   :else (println "(phys/move) unrecognised input:" cmd)))

(defn move
  [cmd game]
  (if-not (nil? cmd)
    (apply-command cmd game)
    game))

;; Gravity

(defn has-weight?
  [entity]
  (and (ent/has-attr entity :position)
       (ent/has-attr entity :gravity)))

(defn gravity
  [game]
  (for [entity game]
    (if (has-weight? entity)
      (let [vel (ent/attr entity [:jump :velocity])
            new-vel (+ vel gravity)]
        (ent/attr entity [:jump :velocity] new-vel))
      entity)))
