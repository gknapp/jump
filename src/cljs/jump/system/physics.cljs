(ns jump.system.physics
  (:require [jump.entity :as ent]))

(def gforce 0.5) ; gravitational pull
(def height 10)  ; jump height
(def step 8)     ; walk speed

;; Walking ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn xor
  [a b]
  (and (or a b) (not (= a b))))

(defn walk?
  "walk left or right in active commands?"
  [cmd]
  (xor (contains? cmd :left)
       (contains? cmd :right)))

(defn can-walk?
  "entity has a position, can walk and takes input commands"
  [entity]
  (and (ent/has-attr entity :position)
       (ent/has-attr entity :walk)
       (ent/has-attr entity :input)))

(defn walk-entity
  [cmd entity]
  (if (can-walk? entity)
    (let [dir (if (contains? cmd :left) :left :right)
          op (if (= dir :left) - +)
          x (ent/attr entity [:position :x])]
      (ent/update entity {:position {:x (op x step)}
                          :walk {:facing dir}}))
    entity))

(defn walk
  [cmd world]
  (if (walk? cmd)
    (map #(walk-entity cmd %) world)
    world))

;; Jumping ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn jump?
  "is jump in active commands?"
  [cmd]
  (contains? cmd :jump))

(defn can-jump?
  [entity]
  (and (ent/has-attr entity :position)
       (ent/has-attr entity :jump)
       (ent/has-attr entity :input)))

(defn on-ground?
  [entity]
  (ent/attr entity [:jump :on-ground]))

(defn set-jump-velocity
  [entity]
  (if (can-jump? entity)
    (ent/attr entity [:jump :velocity] height)
    entity))

; gravity applied to velocity in (gravity)
(defn elevate
  [entity vel]
  (let [y (ent/attr entity [:position :y])]
    (ent/attr entity [:position :y] (- y vel))))

(defn start-jump
  [entity]
  (if (can-jump? entity)
    (if (on-ground? entity)
      (let [jumping (set-jump-velocity entity)]
        (ent/attr jumping [:jump :on-ground] false))
      (elevate entity (ent/attr entity [:jump :velocity])))
    entity))

(defn end-jump
  [entity]
  (if (can-jump? entity)
    (let [v (ent/attr entity [:jump :velocity])
          limit (/ height 2)
          new-v (if (> v limit) limit v)]
      (elevate entity new-v))
    entity))

(defn jump
  [cmd entities]
  (if (jump? cmd)
    (map start-jump entities)
    (map end-jump entities)))

;; Gravity ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn has-weight?
  [entity]
  (and (ent/has-attr entity :position)
       (ent/has-attr entity :gravity)
       (false? (ent/attr entity [:jump :on-ground]))))

(defn gravity
  "apply gravity to jump velocity"
  [world]
  (for [entity world]
    (if (has-weight? entity)
      (let [vel (ent/attr entity [:jump :velocity])]
        (ent/attr entity [:jump :velocity] (- vel gforce)))
      entity)))
