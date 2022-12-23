package stb.com.vn.grpc.server.controllers;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import stb.com.vn.*;
import stb.com.vn.services.WalletException;
import stb.com.vn.services.WalletService;

import static io.grpc.Status.INVALID_ARGUMENT;

@GrpcService
public class WalletController extends WalletGrpc.WalletImplBase {
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public void balance(BalanceRequest request, StreamObserver<BalanceReply> responseObserver) {
        String userId = request.getUserId();
        BalanceReply.Builder builder = BalanceReply.newBuilder();
        walletService.getBalances(userId).forEach(
                (currency, amount) -> builder.addBalance(
                        Balance.newBuilder().setCurrency(currency).setAmount(amount).build()
                )
        );
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deposit(ChangeBalanceRequest request, StreamObserver<Empty> responseObserver) {
        try {
            walletService.deposit(request.getUserId(), request.getCurrency(), request.getAmount());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (WalletException e) {
            logger.warn(e.getMessage(), e);
            responseObserver.onError(new StatusRuntimeException(INVALID_ARGUMENT));
        }
    }

    @Override
    public void withdraw(ChangeBalanceRequest request, StreamObserver<Empty> responseObserver) {
        try {
            walletService.withdraw(request.getUserId(), request.getCurrency(), request.getAmount());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (WalletException e) {
            logger.warn(e.getMessage(), e);
            responseObserver.onError(new StatusRuntimeException(INVALID_ARGUMENT));
        }
    }

//    @Override
//    public void getAuthor(Author request, StreamObserver<Author> responseObserver) {
//        TempDB.getAuthorsFromTempDb()
//                .stream()
//                .filter(author -> author.getAuthorId() == request.getAuthorId())
//                .findFirst()
//                .ifPresent(responseObserver::onNext);
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    public void getBooksByAuthor(Author request, StreamObserver<Book> responseObserver) {
//        TempDB.getBooksFromTempDb()
//                .stream()
//                .filter(book -> book.getAuthorId() == request.getAuthorId())
//                .forEach(responseObserver::onNext);
//        responseObserver.onCompleted();
//    }
//
//
//
//
//    @Override
//    public StreamObserver<Book> getExpensiveBook(StreamObserver<Book> responseObserver) {
//        return new StreamObserver<Book>() {
//            Book expensiveBook = null;
//            float priceTrack = 0;
//
//            @Override
//            public void onNext(Book book) {
//                if (book.getPrice() > priceTrack) {
//                    priceTrack = book.getPrice();
//                    expensiveBook = book;
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                responseObserver.onError(throwable);
//            }
//
//            @Override
//            public void onCompleted() {
//                responseObserver.onNext(expensiveBook);
//                responseObserver.onCompleted();
//            }
//        };
//    }
//
//    @Override
//    public StreamObserver<Book> getBooksByGender(StreamObserver<Book> responseObserver) {
//        return new StreamObserver<Book>() {
//            List<Book> bookList = new ArrayList<>();
//
//            @Override
//            public void onNext(Book book) {
//                TempDB.getBooksFromTempDb()
//                        .stream()
//                        .filter(bookFromDb -> bookFromDb.getAuthorId() == book.getAuthorId())
//                        .forEach(bookList::add);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                responseObserver.onError(throwable);
//            }
//
//            @Override
//            public void onCompleted() {
//                bookList.forEach(responseObserver::onNext);
//                responseObserver.onCompleted();
//            }
//        };
//    }
}
