### Public Transport Disruption Notifier

This app runs a scheduled task that scrapes data from the S-Bahn Berlin website
and notifies users about disruptions via webhook.

It uses a real browser to scrape the data and run JS scripts to load the
entire page and support SPAs.

Runs every 5 minutes by default

## Data Flow Diagram (Mermaid)

```mermaid
flowchart TD
    A[TaskScheduler] -->|Runs every 5 minutes| B[ProviderService]
    B -->|For each provider| C[WebScraper]
    C -->|Scrapes URL| D[Selenium WebDriver]
    D -->|Returns loaded page| E[SBahnDataProcessor]
    E -->|Extracts disruption data| F[SBahnDisruptionData]
    F -->|Converts to formatted string| G[DataToFormatedStringConverter]
    G -->|Returns formatted string| H[OutputData]
    H -->|Checks if data changed| I[ProviderCache]
    I -->|If data changed| J[WebHookWriter]
    J -->|Sends notification| K[Webhook]
```
