(ns jump.system.player)

(enable-console-print!)

; taken from
; https://github.com/google/closure-library/blob/master/closure/goog/events/keynames.js
(def keyname
  {27 :esc
   32 :space
   37 :left
   38 :up
   39 :right
   65 :a
   68 :d
   83 :s
   87 :w})

(def command
  {:esc   :pause
   :space :jump
   :up    :jump
   :w     :jump
   :left  :left
   :a     :left
   :right :right
   :d     :right})

(def commands (atom #{}))

(defn input->command
  [input]
  (-> (.-keyCode input) keyname command))

(defn add-cmd
  [atom input]
  (when-let [cmd (input->command input)]
    (swap! atom conj cmd)))

(defn clr-cmd
  [atom input]
  (when-let [cmd (input->command input)]
    (swap! atom disj cmd)))

(defn bind-controls
  "maintain set of active input commands"
  []
  (set! (.-onkeydown js/document)
        #(add-cmd commands %))
  (set! (.-onkeyup js/document)
        #(clr-cmd commands %)))
