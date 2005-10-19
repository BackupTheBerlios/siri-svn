package org.siri.core.config;

/**
 * Class description.
 */
public class ArchiveHandler
{
    public CronJob getCronJob()
    {
        return cronJob;
    }

    public void setCronJob(CronJob cronJob)
    {
        this.cronJob = cronJob;
    }

    CronJob cronJob;
}
