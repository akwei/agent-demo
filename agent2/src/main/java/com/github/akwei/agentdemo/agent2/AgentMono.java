package com.github.akwei.agentdemo.agent2;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

public class AgentMono<T> extends Mono<T> {
    private final Mono<T> delegate;

    public AgentMono(Mono<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        // do begin process
        this.delegate.subscribe(actual);
    }

    class AgentCoreSubscriber<T> implements CoreSubscriber<T> {
        private final CoreSubscriber<T> delegate;

        public AgentCoreSubscriber(CoreSubscriber<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.delegate.onSubscribe(s);
        }

        @Override
        public void onNext(T t) {
            this.delegate.onNext(t);
        }

        public void onError(Throwable t) {
            this.delegate.onError(t);
            // do error process
        }
        public void onComplete() {
            this.delegate.onComplete();
            // do after process
        }
    }
}
