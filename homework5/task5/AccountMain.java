package homework5.task5;

import java.util.concurrent.CompletableFuture;

public class AccountMain {

    public static void main(String[] args) {
        Account firstAccount = new Account(100_000);
        Account secondAccount = new Account(100_000);

        AccountThread firstThread = new AccountThread(firstAccount, secondAccount, 100);
        AccountThread secondThread = new AccountThread(secondAccount, firstAccount, 100);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(firstThread),
                CompletableFuture.runAsync(secondThread)
        ).join();

        System.out.println("Баланс на аккаунте 1: " + firstAccount.getCacheBalance());
        System.out.println("Баланс на аккаунте 2: " + secondAccount.getCacheBalance());
    }
}
