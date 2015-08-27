# 401kStrategySimulator
Very simple simulator that tries a variety of 401k fund buying strategies

This program has been tested with the historical price CSV files from
Yahoo Finance

The current strategies test are:
1) Continuous investor - buys into the asset as soon as 401 balance is
sufficient to purchase a share
2) Simple dip investor - buys into the asset when it dips below
a certain percentage over a period of days
3) Trend watching dip investor - buys into the asset when it dips below
a certain percentage over a period of days, but has stabilized to a certain
variability over a given timeframe