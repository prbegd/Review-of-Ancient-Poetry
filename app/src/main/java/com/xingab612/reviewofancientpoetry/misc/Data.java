package com.xingab612.reviewofancientpoetry.misc;

import android.app.Activity;
import android.content.res.AssetManager;

import com.xingab612.reviewofancientpoetry.beans.AncientPoetry;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetryType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public class Data {
    private final Activity activity;
    private final String fileName = "data.dat";
    private static ArrayList<AncientPoetryType> data;
    private static final String DEFAULT_DATA_ASSET_PATH = "defaultPoetry.json";

    public Data(Activity activity) {
        this.activity = activity;
    }

    /**
     * 读取数据文件<br>
     * 注: <strong>在更改数据后, 必须调用 {@link #save()} 方法保存数据到文件中</strong>
     *
     * @return 数据列表
     */
    public ArrayList<AncientPoetryType> readData() {
        if (data != null) {
            return data;
        }

        try (FileInputStream fis = activity.openFileInput(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            data = (ArrayList<AncientPoetryType>) ois.readObject();
        } catch (FileNotFoundException e) {
            writeDefaultData(); // 当文件未找到时写入默认数据
        } catch (IOException | ClassNotFoundException e) {
            throw new IORuntimeException("读取数据文件时发生错误!!", e);
        }
        return data;
    }

    public void writeData(ArrayList<AncientPoetryType> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(activity.openFileOutput(fileName, Activity.MODE_PRIVATE))) {
            oos.writeObject(data);
            Data.data = data;
        } catch (IOException e) {
            throw new IORuntimeException("写入数据文件时发生错误!!", e);
        }
    }

    // 将数据保存到文件中
    public void save() {
        writeData(readData());
    }

    private void writeDefaultData() {
        writeData(createDefaultData());
    }

    private ArrayList<AncientPoetryType> createDefaultData() {
        ArrayList<AncientPoetryType> dataList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(readDefaultDataFromAsset());
        JSONArray typeList = jsonObject.getJSONArray("types");
        typeList.forEach(type -> {
            AncientPoetryType poetryType = new AncientPoetryType(((JSONObject) type).getStr("title"));
            JSONArray poetryList = ((JSONObject) type).getJSONArray("poetry");
            if (poetryList != null) {
                ArrayList<AncientPoetry> poetry = poetryType.getPoetryList();
                poetryList.forEach(poetryObj -> {
                    AncientPoetry poetryItem = new AncientPoetry(((JSONObject) poetryObj).getStr("title"));
                    poetry.add(poetryItem);
                });
            }
            dataList.add(poetryType);
        });

        return dataList;
    }

    private String readDefaultDataFromAsset() {
        AssetManager assetManager = activity.getAssets();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(DEFAULT_DATA_ASSET_PATH)))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new IORuntimeException("在读取默认数据文件时发生错误!!", e);
        }
    }
}

