package com.ea.model;

/**
 * Author Parham
 * Since 8/11/14.
 */
public class MoveObject {
        int from;
        int to;
        int peopleWaiting;

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

    public int getPeopleWaiting() {
        return peopleWaiting;
    }

    public void setPeopleWaiting(int peopleWaiting) {
        this.peopleWaiting = peopleWaiting;
    }
}
