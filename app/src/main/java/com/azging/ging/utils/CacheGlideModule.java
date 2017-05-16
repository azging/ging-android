package com.azging.ging.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 *
 *
 */

public class CacheGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int memoryCacheSize = calculator.getMemoryCacheSize();

        DiskLruCacheFactory factory = new ExternalCacheDiskCacheFactory(context, "image_cache", DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
        builder.setDiskCache(factory);


        LruResourceCache cache = new LruResourceCache((int) (1.2 * memoryCacheSize));
        builder.setMemoryCache(cache);


    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
