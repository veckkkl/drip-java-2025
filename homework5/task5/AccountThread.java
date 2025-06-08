package homework5.task5;

/*
вариант с дедлоком:
public class homework6.task5.AccountThread implements Runnable {
    private final homework6.task5.Account accountFrom;
    private final homework6.task5.Account accountTo;
    private final int money;

    public homework6.task5.AccountThread(homework6.task5.Account accountFrom, homework6.task5.Account accountTo, int money) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.money = money;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4000; i++) {
            synchronized (accountFrom) {
                synchronized (accountTo) {
                    if (accountFrom.takeOffMoney(money)) {
                        accountTo.addMoney(money);
                    }
                }
            }
        }
    }
}*/
public class AccountThread implements Runnable {
    private final Account accountFrom;
    private final Account accountTo;
    private final int money;

    public AccountThread(Account accountFrom, Account accountTo, int money) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.money = money;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4000; i++) {
            Account firstLock = accountFrom.hashCode() < accountTo.hashCode() ? accountFrom : accountTo;
            Account secondLock = accountFrom.hashCode() < accountTo.hashCode() ? accountTo : accountFrom;

            synchronized (firstLock) {
                synchronized (secondLock) {
                    if (accountFrom.takeOffMoney(money)) {
                        accountTo.addMoney(money);
                    }
                }
            }
        }
    }
}