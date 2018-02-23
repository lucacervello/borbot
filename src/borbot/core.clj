(ns borbot.core
  (:require [clojure.core.async :refer [<!!]]
            [clojure.string :as str]
            [environ.core :refer [env]]
            [morse.handlers :as h]
            [morse.polling :as p]
            [morse.api :as t]
            [borbot.db :as borbot-db])
  (:gen-class))

(def token (env :telegram-token))


(h/defhandler handler

  (h/command-fn "start"
    (fn [{{id :id :as chat} :chat}]
      (println "Bot joined new chat: " chat)
      (t/send-text token id "Ciao, io sono BORBOT. \n Fedele seguace del Maestro Andreetta. \n brborbrrborbrbro.")))

  (h/command-fn "help"
    (fn [{{id :id :as chat} :chat}]
      (println "Help was requested in " chat)
      (t/send-text token id "Help is on the way")))

  (h/command-fn "cit"
                (fn [{{id :id :as chat} :chat}]
                  (println "Fetching the cit")
                  (t/send-text token id (borbot-db/random-cit))))

  (h/message-fn
    (fn [{{id :id} :chat :as message}]
      (println "Intercepted message: " message)
      (t/send-text token id (apply str (repeat (inc (rand-int 10)) "bor"))))))


(defn -main
  [& args]
  (when (str/blank? token)
    (println "Please provde token in TELEGRAM_TOKEN environment variable!")
    (System/exit 1))

  (println "Starting the borbot")
  (<!! (p/start token handler)))
