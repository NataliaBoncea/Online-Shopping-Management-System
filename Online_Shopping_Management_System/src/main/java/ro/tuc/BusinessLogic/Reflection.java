package ro.tuc.BusinessLogic;

import ro.tuc.Model.Client;

import javax.swing.table.DefaultTableModel;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class Reflection<T> {
    private final Class<T> type;

    public Reflection() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }
    public static void retrieveProperties(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true); // set modifier to public
            Object value;
            try {
                value = field.get(object);
                System.out.println(field.getName() + "=" + value);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public void setTable(ArrayList<T> objectList, DefaultTableModel model) {
        String header[] = new String[objectList.get(0).getClass().getDeclaredFields().length];
        String fields[] = new String[objectList.get(0).getClass().getDeclaredFields().length];
        int index = 0;
        for (Field field : objectList.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true); // set modifier to public
            Object value;
            try {
                //value = field.get(objectList.get(0));
                header[index++] = field.getName();
                //System.out.println(field.getName() + "=" + value);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        model.setColumnIdentifiers(header);
        model.setRowCount(0);
        try {
            for (Object o : objectList) {
                index = 0;
                for (Field field : type.getDeclaredFields()) {
                    String getterMethodName = "get" + field.getName().substring(0, 1).toUpperCase() +
                            field.getName().substring(1);
                    Method getterMethod = o.getClass().getMethod(getterMethodName);
                    fields[index++] = getterMethod.invoke(o).toString();
                }
                model.addRow(fields);
            }
        }catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
